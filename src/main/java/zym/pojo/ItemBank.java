package zym.pojo;

public class ItemBank {
    private Integer id;

    private String itemName;

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemBank itemBank = (ItemBank) o;

        if (id != null ? !id.equals(itemBank.id) : itemBank.id != null) return false;
        if (itemName != null ? !itemName.equals(itemBank.itemName) : itemBank.itemName != null) return false;
        return description != null ? description.equals(itemBank.description) : itemBank.description == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (itemName != null ? itemName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}