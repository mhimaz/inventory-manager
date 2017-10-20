package com.inventory.manager.application.shared.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.inventory.manager.exception.BadRequestException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginationDTO {

    private long total;

    private int count;

    private long totalPages;

    private int currentPage;

    public PaginationDTO() {
    }

    public static PaginationDTO getInstance(long total, int count, int totalPages, int currentPage) {

        return new PaginationDTO(total, count, totalPages, currentPage);
    }

    public static void validatePageAndCountIndexes(int page, int count) {

        if (page < 0) {
            throw new BadRequestException("Invalid page index");
        }

        if (count < 0) {
            throw new BadRequestException("Invalid page size");
        }
    }

    private PaginationDTO(long total, int count, int totalPages, int currentPage) {

        this.total = total;
        this.count = count;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

}
