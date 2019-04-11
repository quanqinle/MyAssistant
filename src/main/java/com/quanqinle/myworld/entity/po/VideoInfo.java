package com.quanqinle.myworld.entity.po;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author quanql
 */
@Entity
@Data
@Table(name = "video_info")
public class VideoInfo {
	/**
	 * Constructor for jpa
	 */
	public VideoInfo() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int videoId;

	private String videoName;
	private String videoSn;
	private int sourceSiteId;
	@Column(name = "create_time")
	private LocalDateTime createTime;
	@Column(name = "update_time")
	private LocalDateTime updateTime;
}
