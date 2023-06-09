package com.example.jpablog.extra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiResultResponseBodyItems {

    List<OpenApiResultResponseBodyItemsItem> item;
}
