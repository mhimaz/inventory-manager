package com.inventory.manager.application.item.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.inventory.manager.application.shared.dto.BaseResponseDTO;
import com.inventory.manager.application.shared.dto.LocationQuantityDTO;
import com.inventory.manager.application.supplier.dto.GetSupplierResponseDTO;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class GetItemResponseDTO extends BaseResponseDTO {

	private Integer id;

	private String name;

	private String code;

	private GetSupplierResponseDTO supplier;

	private List<LocationQuantityDTO> locationQuantities;

	private Long totalQuantity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public GetSupplierResponseDTO getSupplier() {
		return supplier;
	}

	public void setSupplier(GetSupplierResponseDTO supplier) {
		this.supplier = supplier;
	}

	public List<LocationQuantityDTO> getLocationQuantities() {
		if (locationQuantities == null) {
			locationQuantities = new ArrayList<LocationQuantityDTO>();
		}
		return locationQuantities;
	}

	public void setLocationQuantities(
			List<LocationQuantityDTO> locationQuantities) {
		this.locationQuantities = locationQuantities;
	}

	public Long getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

}
