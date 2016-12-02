package util;

import org.mybatis.generator.api.ShellRunner;

public class MybatisGenerator {

	public static void main(String[] args) {
		/*String[] pc = new String[3];
		pc[0] = "-configfile";
		pc[1] = "src/test/resources/mybatis/pc-generator-config.xml";
		pc[2] = "-overwrite";
		
		String[] uc = new String[3];
		uc[0] = "-configfile";
		uc[1] = "src/test/resources/mybatis/uc-generator-config.xml";
		uc[2] = "-overwrite";*/
		
		String[] rzg = new String[3];
		rzg[0] = "-configfile";
		rzg[1] = "src/main/resources/config/generatorConfig.xml";
		rzg[2] = "-overwrite";
		
		ShellRunner.main(rzg);
		//ShellRunner.main(uc);
		
		
	}

}
