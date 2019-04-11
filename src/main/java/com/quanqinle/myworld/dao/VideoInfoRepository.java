package com.quanqinle.myworld.dao;

import com.quanqinle.myworld.entity.po.VideoInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author quanql
 */
public interface VideoInfoRepository extends JpaRepository<VideoInfo, Integer> {

	/**
	 * 获取视频信息
	 *
	 * @param videoName
	 * @return
	 */
	VideoInfo findByVideoName(String videoName);

	/**
	 * 获取所有视频信息
	 *
	 * @return
	 */
//	@Override
//	List<VideoInfo> findAll();
}
