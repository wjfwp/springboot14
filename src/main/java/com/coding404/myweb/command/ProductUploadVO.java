package com.coding404.myweb.command;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUploadVO {

	private Integer upload_no; //pk
	private String filename; //실제파일명
	private String filepath; //폴더명
	private String uuid; //난수값
	private LocalDateTime regdate;
	private Integer prod_id; //fk
	private String prod_writer; //fk
}
