package state;

public interface State {

    public void update(float delta);

    public void render();

    public void enter();

    public void exit();
    
    public void dispose();
    
    public void pause();
    
}
