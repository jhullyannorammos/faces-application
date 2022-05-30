package br.com.application.controller.view;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.UnselectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import br.com.application.model.Country;
import br.com.application.service.CountryService;

@Named
@ApplicationScoped
public class PickListView {

	@Inject private CountryService service;

    private DualListModel<String> cities;
    private DualListModel<Country> countries;

    @PostConstruct
    public void init() {
        //Cities
        List<String> citiesSource = new ArrayList<>();
        List<String> citiesTarget = new ArrayList<>();

        citiesSource.add("San Francisco");
        citiesSource.add("London");
        citiesSource.add("Paris");
        citiesSource.add("Istanbul");
        citiesSource.add("Berlin");
        citiesSource.add("Barcelona");
        citiesSource.add("Rome");

        cities = new DualListModel<>(citiesSource, citiesTarget);

        //Countries
        List<Country> countriesSource = service.getCountries().subList(0, 10);
        List<Country> countriesTarget = new ArrayList<>();

        countries = new DualListModel<>(countriesSource, countriesTarget);

    }

    public DualListModel<String> getCities() {
        return cities;
    }

    public void setCities(DualListModel<String> cities) {
        this.cities = cities;
    }

    public CountryService getService() {
        return service;
    }

    public void setService(CountryService service) {
        this.service = service;
    }

    public DualListModel<Country> getCountries() {
        return countries;
    }

    public void setCountries(DualListModel<Country> countries) {
        this.countries = countries;
    }

    public void onTransfer(TransferEvent event) {
        StringBuilder builder = new StringBuilder();
        for (Object item : event.getItems()) {
            builder.append(((Country) item).getName()).append("<br />");
        }

        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Items Transferred");
        msg.setDetail(builder.toString());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    
    public void onSelect(SelectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().getName()));
    }

    public void onUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().getName()));
    }

    public void onReorder() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
    }
}
