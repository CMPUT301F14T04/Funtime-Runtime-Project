package ca.ualberta.cs.funtime_runtime;


/**
 * 
 * Class inherited exactly from CMPUT 301 AndroidElasticSearch lab code
 * @author Pranjali Pokharel
 *
 */

public class SimpleSearchCommand
{
	private String query;
	private String[] fields;

	public SimpleSearchCommand(String query) {
		this(query, null);
	}

	public SimpleSearchCommand(String query, String[] fields) {
		this.query = query;
		this.fields = fields;
	}

	public String getJsonCommand() {
		StringBuffer command = new StringBuffer(
				"{\"query\" : {\"query_string\" : {\"query\" : \"" + query
						+ "\"");

		if (fields != null) {
			command.append(", \"fields\":  [");

			for (int i = 0; i < fields.length; i++) {
				command.append("\"" + fields[i] + "\", ");
			}
			command.delete(command.length() - 2, command.length());

			command.append("]");
		}

		command.append("}}}");

		return command.toString();
	}
}
