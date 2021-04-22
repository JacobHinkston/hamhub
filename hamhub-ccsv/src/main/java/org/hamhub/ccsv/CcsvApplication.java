package org.hamhub.ccsv;

import org.springframework.boot.WebApplicationType;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CcsvApplication implements CommandLineRunner {

	CcsvRunner runner = new CcsvRunner();

	public static void main(String[] args) {

        System.out.println(Arrays.toString(args));
		try {
            if (args[0] != null && args[1] != null) {
                new SpringApplicationBuilder(CcsvApplication.class)
                    .web(WebApplicationType.NONE)
                    .run(new String[] {args[0], args[1]});
            }
        } catch (Exception e) {
            System.out.println("Error executing commands: " + e);
            System.exit(-1);
        }
	}

	@Override
    public void run(String[] args) throws Exception {
        System.out.println("Ccsv Initializing ...");

		try {
			runner.run(args[0], args[1]);
		} catch(Exception e) {
			System.out.println("There was an error starting the runner: " + e);
		}
    }

}
