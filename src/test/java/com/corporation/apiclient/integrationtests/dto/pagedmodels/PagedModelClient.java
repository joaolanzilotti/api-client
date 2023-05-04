package com.corporation.apiclient.integrationtests.dto.pagedmodels;

import com.corporation.apiclient.integrationtests.dto.ClientDTO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@XmlRootElement
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PagedModelClient {

    @XmlElement(name = "content")
    private List<ClientDTO> content;

}
