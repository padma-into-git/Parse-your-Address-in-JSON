import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import java.util.StringTokenizer;

import javax.json.Json;
import javax.json.JsonObject;

public class AddressParser {

	public static void main(String[] args) throws IOException {
		
		System.out.println("\n\nFRIDAY Address Decoder\n\n 1. Enter Address 2. Exit \n Enter Option 1 or 2:\n");
		
		BufferedReader optionScanner = new BufferedReader(new InputStreamReader(System.in));
				
		String option;
		
		option = optionScanner.readLine();
		
		while(!(option.equals("1") || option.equals("2"))) {
			System.out.println("Enter a Valid Option (1) or (2):\n");
			option = optionScanner.readLine();
		}
		
		while(option.equals("1") || option.equals("2")) {
				
			switch(option) {
			case "1":
				System.out.println("Enter the Input Address\n");
				
				BufferedReader inputAddress = new BufferedReader(new InputStreamReader(System.in));
				String addressLine = null;

				addressLine = inputAddress.readLine();
				
				StringTokenizer addressDelimiter = new StringTokenizer(addressLine, ", .");
				
				ArrayList<String> streetList = new ArrayList<String>();
				ArrayList<String> houseNoList = new ArrayList<String>();

				while(addressDelimiter.hasMoreTokens()) {
					
					
					String currentToken = addressDelimiter.nextToken();
					
					char tokenFirstChar = currentToken.charAt(0);
					
					if(isNumber(tokenFirstChar) || currentToken.equalsIgnoreCase("No") || currentToken.equalsIgnoreCase("NO")
							|| currentToken.length() == 1) {
						houseNoList.add(currentToken);
						
					}else {
						streetList.add(currentToken);
					}
					
				}
				
				buildJSONObject(houseNoList, streetList);
				
				break;
				
			case "2":
				System.out.println("\nBYE! Have a Nice Day!!!\n");
				break;
			}
			if(option.equals("2")) {
				break;
			}
			else if(option.equals("1")) {
				
				System.out.println("\n\nFRIDAY Address Decoder\n\n 1. Enter Address 2. Exit \n Enter Option 1 or 2:\n");
				
				optionScanner = new BufferedReader(new InputStreamReader(System.in));
				
				option = optionScanner.readLine();
				
				while(!(option.equals("1") || option.equals("2"))) {
					System.out.println("Enter a Valid Option (1) or (2):\n");
					option = optionScanner.readLine();
				}
			}
		}
		
	}

	private static void buildJSONObject(ArrayList<String> houseNoList, ArrayList<String> streetList) {
		
		String streetName = "";
		String houseNumber = "";
		String numberIdentifier = "";
		boolean numberFound = false;
		Iterator<String> houseNoIterator = houseNoList.iterator();
		while(houseNoIterator.hasNext()) {
			
			if(houseNoList.size() == 1 || numberFound)
			houseNumber += houseNoIterator.next() + " ";
			else {
				numberFound = true;
				
				numberIdentifier = houseNoIterator.next();
				
				if(isNumber(numberIdentifier.charAt(0)) && houseNoList.get(1).length() == 1) {
					houseNumber += numberIdentifier + " ";
				}
			}
			
		}
		if(!"".equals(numberIdentifier) && houseNoList.get(1).length() != 1)
		{
			streetList.add(numberIdentifier);
		}
		Iterator<String> streetNameIterator = streetList.iterator();
		while(streetNameIterator.hasNext())
			streetName += streetNameIterator.next() + " ";
		
		
		JsonObject addressJson = Json.createObjectBuilder()

				.add("street", streetName.trim())
				.add("housenumber", houseNumber.trim()).build();
		
		System.out.println("\b JSON OUTPUT:\n" +addressJson);
				
	}
	

	static boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

	
}

