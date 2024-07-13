package com.neptune.boards.tests.unit.service;

import com.neptune.boards.entity.Board;
import com.neptune.boards.exception.BoardmasterException;
import com.neptune.boards.repository.BoardRepository;
import com.neptune.boards.service.BoardService;
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

public class BoardServiceTest {

    @Mock
    BoardRepository repository;

    @InjectMocks
    private BoardService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create Dashboard: should create and return a new dashboard")
    void createDashboardTest() {
        Board dashboard = Board.builder().name("Test Dashboard").build();
        when(repository.save(any(Board.class))).thenReturn(dashboard);

        Board createdDashboard = service.create(dashboard);

        assertNotNull(createdDashboard);
        assertEquals("Test Dashboard", createdDashboard.getName());
        verify(repository, times(1)).save(any(Board.class));
    }

    @Test
    @DisplayName("Get Dashboard by ID: should return dashboard if found")
    void getDashboardByIdTest() throws BoardmasterException {
        Board dashboard = Board.builder().name("Test Dashboard").build();
        when(repository.findById(1L)).thenReturn(Optional.of(dashboard));

        Board foundDashboard = service.getDashboard(1L);

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
        Board dashboard = Board.builder().name("Test Dashboard").build();
        when(repository.findByName("Test Dashboard")).thenReturn(Optional.of(dashboard));

        Board foundDashboard = service.getDashboard("Test Dashboard");

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
        Board dashboard1 = Board.builder().name("Dashboard 1").build();
        Board dashboard2 = Board.builder().name("Dashboard 2").build();
        when(repository.findAll()).thenReturn(Arrays.asList(dashboard1, dashboard2));

        List<Board> dashboards = service.getAllDashboards();

        assertNotNull(dashboards);
        assertEquals(2, dashboards.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Delete Dashboard by ID: should delete dashboard if found")
    void deleteDashboardByIdTest() throws BoardmasterException {
        Board dashboard = Board.builder().name("Test Dashboard").build();
        when(repository.findById(1L)).thenReturn(Optional.of(dashboard));

        Board deletedDashboard = service.deleteDashboard(1L);

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
        verify(repository, times(0)).delete(any(Board.class));
    }

    @Test
    @DisplayName("Delete Dashboard by Name: should delete dashboard if found")
    void deleteDashboardByNameTest() throws BoardmasterException {
        Board dashboard = Board.builder().name("Test Dashboard").build();
        when(repository.findByName("Test Dashboard")).thenReturn(Optional.of(dashboard));

        Board deletedDashboard = service.deleteDashboard("Test Dashboard");

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
        verify(repository, times(0)).delete(any(Board.class));
    }

    @Test
    @DisplayName("Change Dashboard Name: should update the dashboard name")
    void changeDashboardNameTest() throws BoardmasterException {
        Board dashboard = Board.builder().name("Old Name").build();
        when(repository.findByName("Old Name")).thenReturn(Optional.of(dashboard));
        when(repository.save(any(Board.class))).thenReturn(dashboard);

        Board updatedDashboard = service.changeDashboardName("Old Name");

        assertNotNull(updatedDashboard);
        assertEquals("Old Name", updatedDashboard.getName());
        verify(repository, times(1)).findByName("Old Name");
        verify(repository, times(1)).save(any(Board.class));
    }
}
