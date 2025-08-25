package com.tfm.tfm.response;

public class ProvinceResponse {
  
    private String name;

    public ProvinceResponse(String name) {
		this.name = name;
	}

    public String getName() {
        return name ;
    }

    public void setName(String name ) {
        this.name  = name ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProvinceResponse that = (ProvinceResponse) o;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
