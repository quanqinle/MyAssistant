package com.quanqinle.myworld.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 运行系统命令
 * @author quanql
 */
public class SystemCommandUtils {
	private static Log log = LogFactory.getLog(SystemCommandUtils.class);

	public static void runCMD(@NotNull String... command) {
		ProcessBuilder builder = new ProcessBuilder(command);
		builder.redirectErrorStream(true);
		try {
			final Process process = builder.start();

			// Watch the process
			watch(process);
		} catch (Exception e) {
			log.error(e);
		}

	}

	/**
	 * show running log
	 * @param process
	 */
	private static void watch(final Process process) {

		new Thread() {
			@Override
			public void run() {
				BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = null;
				try {
					while ((line = input.readLine()) != null) {
						System.out.println(line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

}
