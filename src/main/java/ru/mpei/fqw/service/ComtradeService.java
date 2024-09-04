package ru.mpei.fqw.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.mpei.fqw.client.FourierImpl;
import ru.mpei.fqw.client.VectorF;
import ru.mpei.fqw.dto.FaultCurrentDto;
import ru.mpei.fqw.model.FaultCurrentModel;
import ru.mpei.fqw.repository.RepositoryIml;
import ru.mpei.fqw.utils.ComtradeToJson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class ComtradeService {
    @Autowired
    private RepositoryIml repository;

    @Value("${cfgFileName}")
    private String cfgFileName;
    @Value("${datFileName}")
    private String datFileName;
    @Value("${current}")
    private String current;

    public List<FaultCurrentModel> getFaultCurrentInfo(String nameCurrent) {
        return this.repository.getFaultCurrentInfo( nameCurrent);
    }
    //A1
    public List<FaultCurrentModel> getFaultCurrentInfoA1(){

        return this.repository.getFaultCurrentInfoA1();
    }

    //B1
    public List<FaultCurrentModel> getFaultCurrentInfoB1() {
        return this.repository.getFaultCurrentInfoB1();
    }
    //C1
    public List<FaultCurrentModel> getFaultCurrentInfoC1() {
        return this.repository.getFaultCurrentInfoC1();
    }
    //A2
    public List<FaultCurrentModel> getFaultCurrentInfoA2() {
        return this.repository.getFaultCurrentInfoA2();
    }
    //B2
    public List<FaultCurrentModel> getFaultCurrentInfoB2() {
        return this.repository.getFaultCurrentInfoB2();
    }
    //C2
    public List<FaultCurrentModel> getFaultCurrentInfoC2() {
        return this.repository.getFaultCurrentInfoC2();
    }
    //A3
    public List<FaultCurrentModel> getFaultCurrentInfoA3() {
        return this.repository.getFaultCurrentInfoA3();
    }
    //B3
    public List<FaultCurrentModel> getFaultCurrentInfoB3() {
        return this.repository.getFaultCurrentInfoB3();
    }
    //C3
    public List<FaultCurrentModel> getFaultCurrentInfoC3() {
        return this.repository.getFaultCurrentInfoC3();
    }


    public String comtradeToJSON(){
        double  val;
        List<SetCurrent> setCurrent=new ArrayList<>();
        ObjectMapper currentMapper = new ObjectMapper();
        try {
            setCurrent= currentMapper.readerForListOf(SetCurrent.class).readValue(current);
          } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
         ClassLoader classLoader = getClass().getClassLoader();
        InputStream cfgInputStream = classLoader.getResourceAsStream(cfgFileName);
        InputStream datInputStream = classLoader.getResourceAsStream(datFileName);
        List<CfgInfo> cfgInfoList = readCfgFileFromInputStream(cfgInputStream);
        List<List<Integer>> datData = readDatFileFromInputStream(datInputStream);
        List<ComtradeToJson> comtradeList = new ArrayList<>();

        List<FaultCurrentDto> faultCurrentDtoList = new ArrayList<>();
        int stepTime = datData.get(0).get(1)- datData.get(0).get(0);

        List<Double> time = new ArrayList<>();
        int countData= cfgInfoList.size();
        int countMeasurements=datData.get(0).size();
        boolean flag=false;
            for (int j = 0; j < countMeasurements; j++)
               time.add( j * stepTime / 1000.0);
        int k;
        for (int i = 0; i < countData; i++) {
            ComtradeToJson comtrade = new ComtradeToJson();
            List<Double> values = new ArrayList<>();
            List<Double> rms = new ArrayList<>();
            FourierImpl fourier = new FourierImpl(1);
            VectorF vectorF = new VectorF();
            if(cfgInfoList.get(i).getType().equals("analog")) {
                for (k = 0; k < setCurrent.size(); k++)
                    if (cfgInfoList.get(i).getName().equals(setCurrent.get(k).getName())) {
                        flag = true;
                        break;
                    }
                if (flag == true) {
                    for (int j = 0; j < countMeasurements; j++) {
                        val = datData.get(i).get(j) * cfgInfoList.get(i).getK1() + cfgInfoList.get(i).getK2();
                        values.add(val);
                        fourier.process(val, vectorF);
                        //  time.add( j * stepTime / 1000.0);
                        rms.add(vectorF.getMag());
                        if (setCurrent.get(k).getValue() < vectorF.getMag()) {
                            faultCurrentDtoList.add(new FaultCurrentDto(setCurrent.get(k).getName(), vectorF.getMag(), time.get(j)));
                        }
                    }
                } else {
                    for (int j = 0; j < countMeasurements; j++) {
                        val = datData.get(i).get(j) * cfgInfoList.get(i).getK1() + cfgInfoList.get(i).getK2();
                        values.add(val);
                        fourier.process(val, vectorF);
                        //   time.add( j * stepTime / 1000.0);
                        rms.add(vectorF.getMag());
                    }
                }
                flag=false;
                comtrade.setName(cfgInfoList.get(i).getName());
                comtrade.setType("analog");
                comtrade.setValues(values);
                }
            else{
                comtrade.setName(cfgInfoList.get(i).getName());
                comtrade.setType("discrete");
                comtrade.setValues(datData.get(i));
            }
            comtrade.setTimes(time);
            comtrade.setRMS(rms);
            comtradeList.add(comtrade);

        }
           faultCurrentDtoList.forEach(faultCurrentDto -> {
                        this.repository.save(new FaultCurrentModel(faultCurrentDto.getName(), faultCurrentDto.getValue(), faultCurrentDto.getTime()));
                });
        ObjectMapper mapper = new ObjectMapper();
        String dataInJson = null;
        try {
            dataInJson = mapper.writeValueAsString(comtradeList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return dataInJson;
    }



    @SneakyThrows
    private List<CfgInfo> readCfgFileFromInputStream(InputStream inputStream) {
        List<CfgInfo> cfgInfoList = new ArrayList<>();
        cfgInfoList.add(new CfgInfo("Time", null, null, "time"));
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = br.readLine()) != null) {
            val a = StringUtils.split(line, ",");
            if (a.length > 3){
                CfgInfo cfgInfo = new CfgInfo();
                cfgInfo.setName(a[1]);
                if (StringUtils.contains(line, ",P")){
                    cfgInfo.setType("analog");
                    cfgInfo.setK1(Double.parseDouble(a[3]));
                    cfgInfo.setK2(Double.parseDouble(a[4]));
                }
                else {
                    cfgInfo.setType("discrete");
                }
                cfgInfoList.add(cfgInfo);
            }
        }
        return cfgInfoList;
    }

    @SneakyThrows
    private List<List<Integer>> readDatFileFromInputStream(InputStream inputStream){
        List<List<Integer>> listValues = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = br.readLine()) != null) {
            val b = Arrays.stream(Arrays.stream(StringUtils.split(line, ',')).mapToInt(Integer::parseInt).toArray()).boxed().collect(Collectors.toList());
            b.remove(0);
            listValues.add(b);
        }
        return transpose(listValues);
    }

    private <T> List<List<T>> transpose(List<List<T>> matrix) {
        List<List<T>> transposedMatrix = new ArrayList<>();

        int nbOfColumns = -1;
        int nbOfRows = -1;

        // Check if the given list of lists represents a proper matrix, i.e., each row has to feature an equal number of
        // columns
        if (matrix != null && !matrix.isEmpty()) {
            nbOfRows = matrix.size();

            for (List<T> row : matrix) {
                if (nbOfColumns == -1)
                    nbOfColumns = row.size();
                else if (nbOfColumns != row.size())
                    throw new IllegalArgumentException("The given list of lists is not a proper matrix.");
            }
        }

        // transpose the matrix
        for (int i = 0; i < nbOfColumns; i++) {
            List<T> newRow = new ArrayList<T>(nbOfRows);

            for (List<T> row : matrix) {
                newRow.add(row.get(i));
            }

            transposedMatrix.add(newRow);
        }

        return transposedMatrix;
    }


    public String getCurrent() {
        return current;
    }
}
@Data
@AllArgsConstructor
@NoArgsConstructor
class CfgInfo{
    private String name;
    private Double k1;
    private Double k2;
    private String type;
}
@Data
class SetCurrent{
    private String name;
    private Double value;
}