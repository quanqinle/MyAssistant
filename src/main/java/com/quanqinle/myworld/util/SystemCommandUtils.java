package com.quanqinle.myworld.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.*;

/**
 * 运行系统命令
 *
 * @author quanql
 */
public class SystemCommandUtils {
	private static Log log = LogFactory.getLog(SystemCommandUtils.class);

	public static void runCMD(@NotNull String... command) {
		ProcessBuilder builder = new ProcessBuilder(command);
		builder.redirectErrorStream(true);
		try {
			final Process process = builder.start();
			// watch process log
			showRunningLog(process);
		} catch (Exception e) {
			log.error(e);
		}

	}

	/**
	 * 将命令运行时日志显示在标准输出
	 *
	 * @param process
	 */
	private static void showRunningLog(final Process process) {

		ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
		ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
				0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

		Runnable task = () -> {
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			try {
				while ((line = input.readLine()) != null) {
					System.out.println(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
		singleThreadPool.execute(	task);
		singleThreadPool.shutdown();
	}

}
