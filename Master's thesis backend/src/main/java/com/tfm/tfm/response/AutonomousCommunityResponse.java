package com.tfm.tfm.response;

public class AutonomousCommunityResponse {
  
    private String name;

    public AutonomousCommunityResponse(String name) {
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
        AutonomousCommunityResponse that = (AutonomousCommunityResponse) o;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
