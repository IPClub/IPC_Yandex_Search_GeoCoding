package am.ipc.yandexsearch.api;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "AdministrativeAreaName",
        "Locality"
})
public class AdministrativeArea {

    @JsonProperty("AdministrativeAreaName")
    private String administrativeAreaName;
    @JsonProperty("Locality")
    private Locality locality;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("AdministrativeAreaName")
    public String getAdministrativeAreaName() {
        return administrativeAreaName;
    }

    @JsonProperty("AdministrativeAreaName")
    public void setAdministrativeAreaName(String administrativeAreaName) {
        this.administrativeAreaName = administrativeAreaName;
    }

    @JsonProperty("Locality")
    public Locality getLocality() {
        return locality;
    }

    @JsonProperty("Locality")
    public void setLocality(Locality locality) {
        this.locality = locality;
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