package org.example.diablo.test;

public class StructuralFileDTO {
    private String registerNumber;
    private String fileNo;

    public StructuralFileDTO(String registerNumber, String fileNo) {
        this.registerNumber = registerNumber;
        this.fileNo = fileNo;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public String getFileNo() {
        return fileNo;
    }

    @Override
    public String toString() {
        return "FileNo: " + fileNo + " (RegisterNumber: " + registerNumber + ")";
    }
}
