package com.example.attendance.service;

import com.example.attendance.dto.ResponseDto;
import com.example.attendance.models.AttendanceEntity;
import com.example.attendance.models.IclockTransactionEntity;
import com.example.attendance.repository.AttendanceRepository;
import com.example.attendance.repository.IclockTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class IclockTransaction {
    @Autowired
    private IclockTransactionRepository iclockTransactionRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;

    public ResponseDto all(String empCode, String punchTime, int id) {
        ResponseDto responseDto = new ResponseDto();
        List<Object[]> iclockTransactionEntities = iclockTransactionRepository.findBySomeColumn(empCode, punchTime);

        // Retrieve the last element from the list
        int lastIndex = iclockTransactionEntities.size() - 1;
        Object[] lastRow = iclockTransactionEntities.get(lastIndex);
        Object lastElement = lastRow[1]; //  column is at index 1
        System.out.println(lastElement);

        // Retrieve the first element from the list
        Object[] firstRow = iclockTransactionEntities.get(0);
        Object firstElement = firstRow[1]; //  column is at index 1
        System.out.println(firstElement);
        Optional<AttendanceEntity> attendanceEntityOptional = attendanceRepository.findById(id);
        if (attendanceEntityOptional.isPresent()) {
            AttendanceEntity attendanceEntity = attendanceEntityOptional.get();
            attendanceEntity.setInTime(firstElement.toString());
            attendanceEntity.setOutTime(lastElement.toString());
            attendanceRepository.save(attendanceEntity);
        }

        responseDto.setData(iclockTransactionEntities);
        return responseDto;
    }
    public ResponseDto getDate(String puchTime,String empCode){
        ResponseDto responseDto = new ResponseDto();
        List<Map<String, Object>[]> asc = iclockTransactionRepository.findByPunchTimeAndEmpCode(puchTime, empCode);
        List<Map<String,Object>[]> desc=iclockTransactionRepository.descPunchTime(puchTime,empCode);
        Map<String, Object>[] firstPunch = asc.get(0);
        Map<String, Object>[]  lastpunch=desc.get(0);

AttendanceEntity attendanceEntity =new AttendanceEntity();
// Iterate over the entries in the map
        for (Map.Entry<String, Object> entry : firstPunch[0].entrySet()) {
            String columnName = entry.getKey();
            Object columnValue = entry.getValue();

            if (columnValue instanceof java.util.Date dateValue) {
                LocalDateTime dateTime = new Timestamp(dateValue.getTime()).toLocalDateTime();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = dateTime.format(formatter);
                System.out.println(columnName  + ": in time" + formattedDateTime);
                attendanceEntity.setInTime(formattedDateTime);
            }




//            LocalDateTime dateTime = ((java.util.Date) columnValue).toInstant().atZone(ZoneOffset.UTC).toLocalDateTime();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//            String formattedDateTime = dateTime.format(formatter);
//            System.out.println(columnName + ": " + formattedDateTime);
//            ZoneId systemZone = ZoneId.systemDefault();
//
//// Convert formattedDateTime to LocalDateTime
//            LocalDateTime localDateTime = LocalDateTime.parse(formattedDateTime, formatter);
//
//// Convert LocalDateTime to ZonedDateTime using the system's default time zone
//            ZonedDateTime zonedDateTime = localDateTime.atZone(systemZone);



// Set the convertedDateTime to the attendanceEntity
//            attendanceEntity.setInTime(zonedDateTime.toString());
//            System.out.println(zonedDateTime.toString());
        }
        for(Map.Entry<String,Object> entry:lastpunch[0].entrySet()) {
            String columnName = entry.getKey();
            Object columnValue = entry.getValue();

            if (columnValue instanceof java.util.Date) {
                java.util.Date dateValue = (java.util.Date) columnValue;
                LocalDateTime dateTime = new Timestamp(dateValue.getTime()).toLocalDateTime();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = dateTime.format(formatter);
                System.out.println(columnName  + ": out time" + formattedDateTime);
                attendanceEntity.setOutTime(formattedDateTime);
            }


        }
     attendanceRepository.save(attendanceEntity);

        responseDto.setData(asc);
        return  responseDto;
    }

    public ResponseDto allData(){
        ResponseDto responseDto=new ResponseDto();
        List<IclockTransactionEntity>all=iclockTransactionRepository.findAll();
        responseDto.setData(all);
        return  responseDto;

    }

}
