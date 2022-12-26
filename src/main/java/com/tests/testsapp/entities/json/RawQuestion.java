package com.tests.testsapp.entities.json;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "q_content",
        "type",
        "answers"
})
@Generated("jsonschema2pojo")
public class RawQuestion {

    @JsonProperty("q_content")
    private String qContent;
    @JsonProperty("type")
    private String type;
    @JsonProperty("answers")
    private String answers;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("q_content")
    public String getqContent() {
        return qContent;
    }

    @JsonProperty("q_content")
    public void setqContent(String qContent) {
        this.qContent = qContent;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("answers")
    public String getAnswers() {
        return answers;
    }

    @JsonProperty("answers")
    public void setAnswers(String answers) {
        this.answers = answers;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}