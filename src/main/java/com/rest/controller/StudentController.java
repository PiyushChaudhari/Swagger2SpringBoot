package com.rest.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rest.model.Student;
import com.rest.utils.CommonUtils;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/student")
public class StudentController {

	private List<Student> studentList;

	@PostConstruct
	private List<Student> getStudentList() {
		studentList = new ArrayList<>();
		ZoneId zi = ZoneId.systemDefault();
		studentList.add(new Student(1L, "Piyush", "Chaudhari",
				Date.from(LocalDate.of(1986, 3, 27).atStartOfDay(zi).toInstant())));
		studentList.add(
				new Student(2L, "Alpan", "Patel", Date.from(LocalDate.of(1988, 5, 15).atStartOfDay(zi).toInstant())));
		studentList.add(
				new Student(3L, "Hardik", "Shah", Date.from(LocalDate.of(1989, 11, 5).atStartOfDay(zi).toInstant())));
		studentList.add(
				new Student(4L, "Sandip", "Modi", Date.from(LocalDate.of(1987, 10, 13).atStartOfDay(zi).toInstant())));
		studentList.add(new Student(5L, "Nikhil", "Makwana",
				Date.from(LocalDate.of(1985, 12, 3).atStartOfDay(zi).toInstant())));
		return studentList;
	}

	@ApiOperation(value = "Get list of Students in the System ", response = Map.class, tags = "getStudentList")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> list() {

		Map<String, Object> map = CommonUtils.getDefaultMap();
		map.put(CommonUtils.STATUS_KEY, HttpStatus.OK.value());
		map.put(CommonUtils.DESCRIPTION_KEY, studentList.size() + " record found.");
		map.put(CommonUtils.VALUE_OBJECT_KEY, studentList);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@ApiOperation(value = "Get Student information using id.", response = Map.class, tags = "getStudentById")
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> get(@PathVariable(name = "id", required = true) Long id) {

		Map<String, Object> map = CommonUtils.getDefaultMap();

		Student student = studentList.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);

		if (student != null) {
			map.put(CommonUtils.STATUS_KEY, HttpStatus.OK.value());
			map.put(CommonUtils.DESCRIPTION_KEY, "Student found with id " + id);
			map.put(CommonUtils.VALUE_OBJECT_KEY, student);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			map.put(CommonUtils.STATUS_KEY, HttpStatus.NOT_FOUND.value());
			map.put(CommonUtils.DESCRIPTION_KEY, "Student not found with id " + id);
			map.remove(CommonUtils.VALUE_OBJECT_KEY);
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}

	}

	@ApiOperation(value = "Save new student information.", response = Map.class, tags = "saveStudent")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> save(@RequestBody Student student) {

		Map<String, Object> map = CommonUtils.getDefaultMap();

		studentList.add(student);
		map.put(CommonUtils.STATUS_KEY, HttpStatus.OK.value());
		map.put(CommonUtils.DESCRIPTION_KEY, "Student save successfully.");
		map.remove(CommonUtils.VALUE_OBJECT_KEY);
		return new ResponseEntity<>(map, HttpStatus.OK);

	}

	@ApiOperation(value = "update student information.", response = Map.class, tags = "updateStudent")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> update(@PathVariable(name = "id", required = true) Long id,
			@RequestBody Student studentInstance) {

		Map<String, Object> map = CommonUtils.getDefaultMap();

		Student student = studentList.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);

		if (student != null) {
			BeanUtils.copyProperties(studentInstance, student, "id");
			map.put(CommonUtils.STATUS_KEY, HttpStatus.OK.value());
			map.put(CommonUtils.DESCRIPTION_KEY, "Student update successfully with id " + id);
			map.remove(CommonUtils.VALUE_OBJECT_KEY);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			map.put(CommonUtils.STATUS_KEY, HttpStatus.NOT_FOUND.value());
			map.put(CommonUtils.DESCRIPTION_KEY, "Student not found with id " + id);
			map.remove(CommonUtils.VALUE_OBJECT_KEY);
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}

	}

	@ApiOperation(value = "delete student information.", response = Map.class, tags = "deleteStudent")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Map<String, Object>> delete(@PathVariable(name = "id", required = true) Long id) {

		Map<String, Object> map = CommonUtils.getDefaultMap();

		Student student = studentList.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);

		if (student != null) {
			studentList.remove(student);
			map.put(CommonUtils.STATUS_KEY, HttpStatus.OK.value());
			map.put(CommonUtils.DESCRIPTION_KEY, "Student delete successfully with id " + id);
			map.remove(CommonUtils.VALUE_OBJECT_KEY);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			map.put(CommonUtils.STATUS_KEY, HttpStatus.NOT_FOUND.value());
			map.put(CommonUtils.DESCRIPTION_KEY, "Student not found with id " + id);
			map.remove(CommonUtils.VALUE_OBJECT_KEY);
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}

	}

}
