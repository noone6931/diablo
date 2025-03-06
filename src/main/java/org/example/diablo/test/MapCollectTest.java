package org.example.diablo.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapCollectTest {
    public static void main(String[] args) {
        // 示例数据
        List<StructuralFileDTO> files = Arrays.asList(
                new StructuralFileDTO("REG123", "FILE001"),
                new StructuralFileDTO("REG123", "FILE002"),
                new StructuralFileDTO("REG456", "FILE003"),
                new StructuralFileDTO("REG123", "FILE004"),
                new StructuralFileDTO("REG123", "FILE004"),
                new StructuralFileDTO("REG321", "FILE004"),
                new StructuralFileDTO("REG213", "FILE004"),
                new StructuralFileDTO("REG789", "FILE005"),
                new StructuralFileDTO("REG456", "FILE006")
        );

        // 使用 Collectors.groupingBy 和 Collectors.mapping 来分组并收集文件编号
        Map<String, List<String>> groupedFiles = files.stream()
                .collect(Collectors.groupingBy(
                        StructuralFileDTO::getRegisterNumber, // 根据 registerNumber 进行分组
                        Collectors.mapping(StructuralFileDTO::getFileNo, Collectors.toList()) // 收集 fileNo
                ));

        // 打印结果
        groupedFiles.forEach((registerNumber, fileNos) -> {
            System.out.println("Register Number: " + registerNumber);
            System.out.println("File Numbers: " + fileNos);
            System.out.println();
        });

        Map<String, List<String>> map = files.stream().collect(Collectors.toMap(
                StructuralFileDTO::getRegisterNumber,
                file -> {
                    List<String> fileNos = new ArrayList<>();
                    fileNos.add(file.getFileNo());
                    return fileNos;
                },
                (existing, replacement) -> {
                    existing.addAll(replacement);
                    return existing;
                }
        ));
        // 打印结果
        map.forEach((registerNumber, fileNos) -> {
            System.out.println("#Register Number: " + registerNumber);
            System.out.println("#File Numbers: " + fileNos);
            System.out.println();
        });

    }
}
