package chapter3;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;


public class QueryTVSales {

    public void run() {
        List<Pair<String, Integer>> sales = new ArrayList<>();

        sales.add(Pair.of("TV", 2500));
        sales.add(Pair.of("Camera", 300));
        sales.add(Pair.of("TV", 1600));
        sales.add(Pair.of("Phone", 800));

        Maybe<Integer> tvSales = Observable.fromIterable(sales)
                .filter(sale -> "TV".equals(sale.getLeft()))
                .map(sale -> sale.getRight())
                .reduce((sale1, sale2) -> sale1 + sale2);
        tvSales.subscribe(tot -> System.out.println("TV Sales: $" + tot));
    }

    public static void main(String[] args) {
        QueryTVSales demo = new QueryTVSales();
        demo.run();
    }

}
