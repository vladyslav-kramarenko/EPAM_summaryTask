package ua.nure.kramarenko.SummaryTask4.web.command.admin;

import org.apache.log4j.Logger;
import ua.nure.kramarenko.SummaryTask4.db.Path;
import ua.nure.kramarenko.SummaryTask4.db.derby.CharacteristicsDb;
import ua.nure.kramarenko.SummaryTask4.db.entity.Characteristic;
import ua.nure.kramarenko.SummaryTask4.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class CharacteristicsCommand extends Command {

	private static final long serialVersionUID = 1863978254689586513L;

	private static final Logger LOG = Logger
			.getLogger(CharacteristicsCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Commands starts");

		HttpSession session = request.getSession();

		CharacteristicsDb characteristicsDb = new CharacteristicsDb();
		String action = request.getParameter("action");
		LOG.debug("Aciton = " + action);
		String name = request.getParameter("characteristicName");
		String description = request.getParameter("characteristicDescription");
		Characteristic characteristic = null;
		if (action != null) {

			if (action.equals("create")) {
				characteristic = new Characteristic();
				characteristic.setName(name);
				characteristic.setDescription(description);
				characteristicsDb.addCharacteristic(characteristic);
			} else {
				int characteristicId = Integer.parseInt(request
						.getParameter("characteristicId"));
				characteristic = characteristicsDb
						.getCharacteristic(characteristicId);
				if (action.equals("delete")) {
					characteristicsDb.deleteCharacteristic(characteristic);
				}
				if (action.equals("edit")) {
					request.setAttribute("characteristic", characteristic);
					request.setAttribute("action", "edit");
				}
				if (action.equals("update")) {
					characteristic.setName(name);
					characteristic.setDescription(description);
					characteristicsDb.updateCharacteristic(characteristic);
				}
			}
		}

		List<Characteristic> characteristicsList = characteristicsDb
				.getAllCharacteristics();
		request.setAttribute("characteristicsList", characteristicsList);
		session.setAttribute("page", "characteristics");
		return Path.PAGE_CHARACTERISTICS;
	}

}