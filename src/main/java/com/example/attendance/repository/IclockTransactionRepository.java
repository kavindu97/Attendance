package com.example.attendance.repository;

import com.example.attendance.models.IclockTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IclockTransactionRepository extends JpaRepository<IclockTransactionEntity,Integer> {
  @Query(value = "SELECT emp_code, punch_time FROM iclock_transaction WHERE emp_code = :empCode AND DATE(punch_time) = DATE(:punchTime)", nativeQuery = true)
    List<Object[]>  findBySomeColumn(@Param("empCode")  String empCode, @Param ("punchTime") String punchTime);
//List<Object> findByEmpCodeAndPunch_timeIsContainingIgnoreCaseOrderByPunch_time(String empCode, String punchTime);

@Query(value = "select punch_time from iclock_transaction where DATE_FORMAT(punch_time,'%Y-%m-%d') =:punchTime and  emp_code=:empCode order by punch_time asc limit 1",nativeQuery = true)
List<Map<String,Object>[]>findByPunchTimeAndEmpCode(String punchTime, String empCode);
  @Query(value = "select punch_time from iclock_transaction where DATE_FORMAT(punch_time,'%Y-%m-%d') =:punchTime and  emp_code=:empCode order by punch_time desc limit 1",nativeQuery = true)
  List<Map<String,Object>[]> descPunchTime(String punchTime, String empCode);


}
