package br.com.fiap.easyinsurance.cucumber;

import br.com.fiap.easyinsurance.EasyinsuranceApp;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = EasyinsuranceApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
