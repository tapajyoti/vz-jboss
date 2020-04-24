package resporg.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;

public class Basic {
	

	public static void main(String[] args) {
		Map<String, List<OrderVO>> orderMap = new LinkedHashMap<>();
		List<OrderVO> newSubList = null;
		int flag = 1;
		do {

			List<WeightVO> weightList = getWeightCounter();
			Collections.sort(weightList, new Comparator<WeightVO>() {
				@Override
				public int compare(WeightVO p1, WeightVO p2) {
					return p1.getWeight() - p2.getWeight();
				}
			});

			for (WeightVO weightVO : weightList) {
				 System.out.println("Weight: " + weightVO.getAccNo() + ":" + weightVO.getWeight());
			}
			orderMap = getAllOrders();
			newSubList = new ArrayList<OrderVO>();

			
			
			System.out.println(flag + " : iteration ######################################\n");
			newSubList = createSubList(weightList, orderMap);
			
			
			// Executor Service called
			for (OrderVO orderVO : newSubList) {
				System.out.println(orderVO.getAccNo() + ":" + orderVO.getOrderId());
			}
			System.out.println("######################################\n");
			System.out.println("Map Size:"+orderMap.size());
			flag++;
			if (flag > 8) {
				break;
			}
		} while (orderMap.isEmpty());

	}

	private static List<OrderVO> createSubList(List<WeightVO> weightList, Map<String, List<OrderVO>> orderMap) {
		List<OrderVO> newSubList = new ArrayList<OrderVO>();
		int thresHold = 4;
		ListIterator<WeightVO> iterator = weightList.listIterator();
		
		while(iterator.hasNext()) {
			WeightVO a=iterator.next();
			List<OrderVO> orderList = orderMap.get(a.getAccNo());
			
			if (orderList.size() < thresHold) {
				newSubList.addAll(orderList);
				orderMap.remove(a.getAccNo());
			} else {
				newSubList.addAll(orderList.subList(0, thresHold));
				orderMap.remove(a.getAccNo());
			}

			a.setWeight(a.getWeight() + 1);
		}
		
		// Increase the weightCounter of this AccountNumber by 1 and save in the DB.
		return newSubList;
		
		
		
		/*for (WeightVO weightVO : weightList) {	
			System.out.println("Accno: " + weightVO.getAccNo() + ":" + weightVO.getWeight());
			List<OrderVO> orderList = orderMap.get(weightVO.getAccNo());
			int thresHold = 4;
			System.out.println("Creating Sublist for Accno " + weightVO.getAccNo());
			if (orderList.size() < thresHold) {
				newSubList.addAll(orderList);
				orderMap.remove(weightVO.getAccNo());
				//newSubList.addAll(createSubList(weightList, orderMap));
			} else {
				newSubList.addAll(orderList.subList(0, thresHold));
				orderMap.remove(weightVO.getAccNo());
			}
			weightVO.setWeight(weightVO.getWeight()+1);
			// Increase the weightCounter of this AccountNumber by 1 and save in the DB.
		}
		return newSubList;*/
	}

	private static Map<String, List<OrderVO>> getAllOrders() {
		Map<String, List<OrderVO>> orderMap = new LinkedHashMap<>();

		List<OrderVO> orderList1 = new ArrayList<>();
		for (int i = 1; i <= 15; i++) {
			OrderVO ov = new OrderVO();
			ov.setAccNo("g1");
			ov.setOrderId(i);
			orderList1.add(ov);
		}

		List<OrderVO> orderList2 = new ArrayList<>();
		for (int i = 16; i <= 18; i++) {
			OrderVO ov = new OrderVO();
			ov.setAccNo("g2");
			ov.setOrderId(i);
			orderList2.add(ov);
		}
		List<OrderVO> orderList3 = new ArrayList<>();
		for (int i = 26; i <= 33; i++) {
			OrderVO ov = new OrderVO();
			ov.setAccNo("g3");
			ov.setOrderId(i);
			orderList3.add(ov);
		}
		List<OrderVO> orderList4 = new ArrayList<>();
		for (int i = 34; i <= 44; i++) {
			OrderVO ov = new OrderVO();
			ov.setAccNo("g4");
			ov.setOrderId(i);
			orderList4.add(ov);
		}
		orderMap.put("g1", orderList1);
		orderMap.put("g2", orderList2);
		orderMap.put("g3", orderList3);
		orderMap.put("g4", orderList4);
		return orderMap;
	}

	private static List<WeightVO> getWeightCounter() {
		List<WeightVO> weightList = new ArrayList<>();
		Random rn = new Random();
		for (int i = 0; i < 4; i++) {
			WeightVO wvo = new WeightVO();
			wvo.setAccNo("g" + String.valueOf(i + 1));
			wvo.setWeight(rn.nextInt(10) + 1);
			weightList.add(wvo);
		}
		return weightList;
	}
}
