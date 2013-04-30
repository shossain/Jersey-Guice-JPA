package tas.queue.service.config;

public class QSConstants {
	public class QueryParameters {
		public static final String userId = "userId";
		public static final String apiKey = "apiKey";
		public static final String visibilityTimeout = "visibilityTimeout";
		public static final String expirationTimeout = "expirationTimeout";		
	}
	
	public class FormParameters {
		public static final String messageText = "messageText";
	}
	
	public class Path {
		public static final String queues = "/queues";
		public static final String messages = "/messages";
		public static final String root = "/v1/user/*";
	}
	
	public class PathParameters {
		public static final String queueName = "queueName";
		public static final String messageId = "messageId";
	}
	
	public class Ranges {
		public class VisibilityTimeout {
			public static final long min = 0;
			public static final long max = 43200;  //12 hours
		}
		
		public class ExpirationTimeout {
			public static final long min = 60;
			public static final long max = 1209600; //14 days
		}
		
		public class MessageText {
			public static final int length = 65536; //64 KB
		}
		
		public class QueueName {
			public static final int length = 50;
		}
	}
}
