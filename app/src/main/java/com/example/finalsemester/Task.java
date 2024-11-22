package com.example.finalsemester;

public class Task {
    private String name; // Tên của công việc (task)
    private String description; // Mô tả chi tiết công việc (task)

    // Constructor: Khởi tạo đối tượng Task với tên và mô tả
    public Task(String name, String description) {
        this.name = name; // Gán giá trị cho thuộc tính name
        this.description = description; // Gán giá trị cho thuộc tính description
    }

    // Getter cho name: Lấy tên công việc
    public String getName() {
        return name; // Trả về giá trị của thuộc tính name
    }

    // Setter cho name: Cập nhật tên công việc
    public void setName(String name) {
        this.name = name; // Gán giá trị mới cho thuộc tính name
    }

    // Getter cho description: Lấy mô tả công việc
    public String getDescription() {
        return description; // Trả về giá trị của thuộc tính description
    }

    // Setter cho description: Cập nhật mô tả công việc
    public void setDescription(String description) {
        this.description = description; // Gán giá trị mới cho thuộc tính description
    }
}
