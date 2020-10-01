public abstract class Observer {
    private Boolean team;
    public abstract void update(UpdateType updateType);

    public void setTeam(Boolean team){
        this.team=team;
    }
    public Boolean getTeam(){
        return team;
    }
}

