package com.lzhpo.sensitive;

import com.lzhpo.sensitive.annocation.IgnoreSensitive;
import com.lzhpo.sensitive.entity.SampleEntity;
import com.lzhpo.sensitive.entity.SampleEntityMock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lzhpo
 */
@RestController
@RequestMapping("/")
public class SensitiveController {

  @GetMapping("hello")
  public String hello() {
    return "hello";
  }

  @GetMapping("sample1")
  public ResponseEntity<SampleEntity> sample1() {
    return ResponseEntity.ok(SampleEntityMock.test1());
  }

  @IgnoreSensitive
  @GetMapping("ignore/sample2")
  public ResponseEntity<SampleEntity> sample2() {
    return ResponseEntity.ok(SampleEntityMock.test1());
  }
}
