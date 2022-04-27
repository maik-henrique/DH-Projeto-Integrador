package br.com.meli.dhprojetointegrador.enums;

public enum DueDateEnum {

    MAX_DUEDATE_WEEKS(3);

    private final int duedate;

    DueDateEnum(int duedate) {
        this.duedate = duedate;
    }

    public int getDuedate(){
        return duedate;
    }
}
