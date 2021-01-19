package socialbook.model;

import java.util.Objects;

public class Follow {
    private int customer;
    private int follower;

    public Follow(){};

    public Follow(int customer, int follower){
        this.customer = customer;
        this.follower = follower;
    }
    public int getFollower() {
        return follower;
    }

    public int getCostumer() {
        return customer;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public void setCostumer(int customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Follow follow = (Follow) o;
        return follower == follow.follower &&
                customer == follow.customer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(follower, customer);
    }

    @Override
    public String toString() {
        return "Follow{" +
                "follower=" + follower +
                ", costumer=" + customer +
                '}';
    }
}
