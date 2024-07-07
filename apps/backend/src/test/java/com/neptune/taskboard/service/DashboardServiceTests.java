package com.neptune.taskboard.service;

import com.neptune.taskboard.entity.Dashboard;
import com.neptune.taskboard.exception.BoardmasterException;
import com.neptune.taskboard.repository.IDashboardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DashboardServiceTests {

    @Mock
    IDashboardRepository repository;

    @InjectMocks
    private DashboardService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create Dashboard: should create and return a new dashboard")
    void createDashboardTest() {
        Dashboard dashboard = new Dashboard("Test Dashboard");
        when(repository.save(any(Dashboard.class))).thenReturn(dashboard);

        Dashboard createdDashboard = service.create("Test Dashboard");

        assertNotNull(createdDashboard);
        assertEquals("Test Dashboard", createdDashboard.getName());
        verify(repository, times(1)).save(any(Dashboard.class));
    }

    @Test
    @DisplayName("Get Dashboard by ID: should return dashboard if found")
    void getDashboardByIdTest() throws BoardmasterException {
        Dashboard dashboard = new Dashboard("Test Dashboard");
        when(repository.findById(1L)).thenReturn(Optional.of(dashboard));

        Dashboard foundDashboard = service.getDashboard(1L);

        assertNotNull(foundDashboard);
        assertEquals("Test Dashboard", foundDashboard.getName());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Get Dashboard by ID: should throw exception if not found")
    void getDashboardByIdNotFoundTest() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BoardmasterException.class, () -> service.getDashboard(1L));
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Get Dashboard by Name: should return dashboard if found")
    void getDashboardByNameTest() throws BoardmasterException {
        Dashboard dashboard = new Dashboard("Test Dashboard");
        when(repository.findByName("Test Dashboard")).thenReturn(Optional.of(dashboard));

        Dashboard foundDashboard = service.getDashboard("Test Dashboard");

        assertNotNull(foundDashboard);
        assertEquals("Test Dashboard", foundDashboard.getName());
        verify(repository, times(1)).findByName("Test Dashboard");
    }

    @Test
    @DisplayName("Get Dashboard by Name: should throw exception if not found")
    void getDashboardByNameNotFoundTest() {
        when(repository.findByName("Test Dashboard")).thenReturn(Optional.empty());

        assertThrows(BoardmasterException.class, () -> service.getDashboard("Test Dashboard"));
        verify(repository, times(1)).findByName("Test Dashboard");
    }

    @Test
    @DisplayName("Get All Dashboards: should return list of dashboards")
    void getAllDashboardsTest() {
        Dashboard dashboard1 = new Dashboard("Dashboard 1");
        Dashboard dashboard2 = new Dashboard("Dashboard 2");
        when(repository.findAll()).thenReturn(Arrays.asList(dashboard1, dashboard2));

        List<Dashboard> dashboards = service.getAllDashboards();

        assertNotNull(dashboards);
        assertEquals(2, dashboards.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Delete Dashboard by ID: should delete dashboard if found")
    void deleteDashboardByIdTest() throws BoardmasterException {
        Dashboard dashboard = new Dashboard("Test Dashboard");
        when(repository.findById(1L)).thenReturn(Optional.of(dashboard));

        Dashboard deletedDashboard = service.deleteDashboard(1L);

        assertNotNull(deletedDashboard);
        assertEquals("Test Dashboard", deletedDashboard.getName());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).delete(dashboard);
    }

    @Test
    @DisplayName("Delete Dashboard by ID: should throw exception if not found")
    void deleteDashboardByIdNotFoundTest() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BoardmasterException.class, () -> service.deleteDashboard(1L));
        verify(repository, times(1)).findById(1L);
        verify(repository, times(0)).delete(any(Dashboard.class));
    }

    @Test
    @DisplayName("Delete Dashboard by Name: should delete dashboard if found")
    void deleteDashboardByNameTest() throws BoardmasterException {
        Dashboard dashboard = new Dashboard("Test Dashboard");
        when(repository.findByName("Test Dashboard")).thenReturn(Optional.of(dashboard));

        Dashboard deletedDashboard = service.deleteDashboard("Test Dashboard");

        assertNotNull(deletedDashboard);
        assertEquals("Test Dashboard", deletedDashboard.getName());
        verify(repository, times(1)).findByName("Test Dashboard");
        verify(repository, times(1)).delete(dashboard);
    }

    @Test
    @DisplayName("Delete Dashboard by Name: should throw exception if not found")
    void deleteDashboardByNameNotFoundTest() {
        when(repository.findByName("Test Dashboard")).thenReturn(Optional.empty());

        assertThrows(BoardmasterException.class, () -> service.deleteDashboard("Test Dashboard"));
        verify(repository, times(1)).findByName("Test Dashboard");
        verify(repository, times(0)).delete(any(Dashboard.class));
    }

    @Test
    @DisplayName("Change Dashboard Name: should update the dashboard name")
    void changeDashboardNameTest() throws BoardmasterException {
        Dashboard dashboard = new Dashboard("Old Name");
        when(repository.findByName("Old Name")).thenReturn(Optional.of(dashboard));
        when(repository.save(any(Dashboard.class))).thenReturn(dashboard);

        Dashboard updatedDashboard = service.changeDashboardName("Old Name");

        assertNotNull(updatedDashboard);
        assertEquals("Old Name", updatedDashboard.getName());
        verify(repository, times(1)).findByName("Old Name");
        verify(repository, times(1)).save(any(Dashboard.class));
    }
}
