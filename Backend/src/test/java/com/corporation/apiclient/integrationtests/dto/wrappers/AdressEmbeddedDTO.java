package com.corporation.apiclient.integrationtests.dto.wrappers;

import com.corporation.apiclient.integrationtests.dto.AdressDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdressEmbeddedDTO implements Serializable {

    @JsonProperty("adressDTOList")
    private List<AdressDTO> adress;

}
