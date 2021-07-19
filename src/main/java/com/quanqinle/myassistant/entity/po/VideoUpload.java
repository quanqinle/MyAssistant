package com.quanqinle.myassistant.entity.po;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author quanql
 */
@Entity
@Data
@Table(name = "video_upload")
public class VideoUpload {
	/**
	 * Constructor for jpa
	 */
	public VideoUpload() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int videoId;
	private int siteId;
	private String postName;
	private int state;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
}
