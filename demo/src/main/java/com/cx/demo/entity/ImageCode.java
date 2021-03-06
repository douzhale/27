/**
 * 
 */
package com.cx.demo.entity;

import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;


@Component("imageCode")
public class ImageCode  {

	/**
	 * 带有数字的验证码图片
	 */
	private BufferedImage bufferedImage;

	/**
	 * 验证码内容
	 */
	private String code;

	/**
	 * 时间
	 */
	private LocalDateTime localDateTime;

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	/**
	 * 设置过期时间
	 * @param bufferedImage
	 * @param code
	 * @param
	 * @return
	 */


	public ImageCode(BufferedImage bufferedImage, String code, LocalDateTime localDateTime) {
		this.bufferedImage = bufferedImage;
		this.code = code;
		this.localDateTime = localDateTime;
	}

	public ImageCode() {
	}

	public ImageCode(BufferedImage bufferedImage,String code,Integer time){
		this.bufferedImage = bufferedImage;
		this.code = code;
		this.localDateTime = LocalDateTime.now().plusSeconds(time);

	}

	public boolean isExpired(){
		return LocalDateTime.now().isAfter(localDateTime);
	}
}
