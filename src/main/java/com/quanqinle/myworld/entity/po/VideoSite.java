package com.quanqinle.myworld.entity.po;

import lombok.Data;

import javax.persistence.*;

/**
 * @author quanql
 */
@Entity
@Data
@Table(name = "video_site")
public class VideoSite {
	/**
	 * Constructor for jpa
	 */
	public VideoSite() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int siteId;

	private String siteName;
	private String homeUrl;
	private String downloadUrl;
	private String uploadUrl;
	private String siteDesc;
	@Lob
	private String cookie;
}
