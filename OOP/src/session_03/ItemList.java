package session_03;

public class ItemList {
    Item[] list;
    int numOfItem;
    final int MAX = 100;
    public ItemList(){
        list = new Item[MAX];
    }

    public boolean addItem(Item item){
        if(item == null || numOfItem >= MAX){
            return false;
        }

        list[numOfItem] = item;
        numOfItem++;

        return true;
    }

    public void displayAll(){
        if(numOfItem == 0){
            System.out.println("No Item yet.");
        }

        for(int i = 0; i< numOfItem;i++){
            System.out.println(list[i]);
        }

    }

    public Item findItemByCreator(String creator){
        String key = creator.toLowerCase();
        Item newItem = new Item();
        for(int i =0;i<numOfItem;i++){
            if(list[i].creator.equals(key)){
                newItem = list[i];
            }
        }
        return newItem;
    }

    public boolean updateItem(String id){
        if(id == null || id.trim().isEmpty()){
            return false;
        }
        for(int i = 0;i<numOfItem;i++){
            if(list[i].getId() != null && list[i].getId().equalsIgnoreCase(id)){
                list[i].input();
                return true;
            }
        }
        return false;
    }

    public void displayItemByType(String type){
        if(type.equals("VASE")){
            for(int i =0;i<numOfItem;i++){
                if(list[i] instanceof Vase){
                    System.out.println(list[i]);
                }
            }
        }else if(type.equals("STATUE")){
            for(int i = 0; i< numOfItem;i++){
                if(list[i] instanceof Statue){
                    System.out.println(list[i]);
                }
            }
        }else if (type.equals("PAINTING")){
            for (int i = 0;i < numOfItem;i++){
                if(list[i] instanceof Painting){
                    System.out.println(list[i]);
                }
            }
        }
    }
}
