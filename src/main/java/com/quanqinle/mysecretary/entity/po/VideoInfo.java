package com.quanqinle.mysecretary.entity.po;

import io.swagger.annotations.ApiModelProperty;
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
	@ApiModelProperty(notes = "video Id")
	private int videoId;

	@ApiModelProperty(notes = "video name", required = true)
	private String videoName;
	@ApiModelProperty(notes = "unique code in video website")
	private String videoSn;
	@ApiModelProperty(notes = "video website Id")
	private int sourceSiteId;
	@Column(name = "create_time")
	private LocalDateTime createTime;
	@Column(name = "update_time")
	private LocalDateTime updateTime;
}
