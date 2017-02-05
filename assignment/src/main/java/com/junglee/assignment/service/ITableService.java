package com.junglee.assignment.service;

import java.io.IOException;

import com.junglee.assignment.dto.PlayerDTO;
import com.junglee.assignment.dto.TableDTO;
import com.junglee.assignment.exception.TableCreateException;
import com.junglee.assignment.exception.TableNotAvailabelException;
import com.junglee.assignment.exception.TableNotFoundException;

public interface ITableService {
	
	public TableDTO createTable(PlayerDTO player) throws TableCreateException, IOException;
	
	public TableDTO getById(int tableId) throws IOException;
	
	public String getTableState(int tableId) throws TableNotFoundException;
	
	public boolean isTableAvailable(int tableId) throws TableNotFoundException;
	
	public TableDTO joinTable(PlayerDTO playerDTO, TableDTO tableDTO) throws TableNotAvailabelException, IOException;

}
