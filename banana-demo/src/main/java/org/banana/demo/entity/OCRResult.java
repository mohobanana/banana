package org.banana.demo.entity;

import javafx.geometry.BoundingBox;

import java.util.List;

@Data
public class OCRResult {
    private String text;
    private List<BoundingBox> boundingBoxes;
    private Double confidence;
    private String language;
}
