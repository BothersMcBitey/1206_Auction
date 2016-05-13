package communication.messages;

public enum MessageType {
Connect, 			LogIn, 				LogOff, 				Register, 
CreateAuction, 		Bid, 				SearchAuctionsRequest, 	SearchAuctionsResult, 
Notification, 		ModifyUser, 		ViewAuctionRequest, 	ViewAuctionData,
ViewUserRequest,	ViewUserData, 		ViewHomeRequest, 		ViewHomeScreenData, 	 
Success, 			Failure 
}
