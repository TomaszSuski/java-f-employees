package com.lingarogroup;

public enum EmployeeRoles {
    PROGRAMMER("Programmer"),
    ANALYST("Analyst"),
    MANAGER("Manager"),
    CEO("CEO");

    private final String role;

    EmployeeRoles(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return role;
    }
}
