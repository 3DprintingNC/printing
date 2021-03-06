package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.model.entity.*;
import com.netcracker.educ.printing.model.repository.ExecutorEquipmentRepo;
import com.netcracker.educ.printing.model.repository.MaterialEquipmentRepo;
import com.netcracker.educ.printing.model.repository.MaterialRepo;
import com.netcracker.educ.printing.model.repository.UserRepo;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class MaterialService {

    private MaterialRepo materialRepo;
    private MaterialEquipmentRepo materialEquipmentRepo;
    private ExecutorEquipmentRepo executorEquipmentRepo;
    private EquipmentService equipmentService;

    public MaterialService(MaterialRepo materialRepo, MaterialEquipmentRepo materialEquipmentRepo, ExecutorEquipmentRepo executorEquipmentRepo, EquipmentService equipmentService) {
        this.materialRepo = materialRepo;
        this.materialEquipmentRepo = materialEquipmentRepo;
        this.executorEquipmentRepo = executorEquipmentRepo;
        this.equipmentService = equipmentService;
    }

    //public List<String> getMaterialsByTitlePart(String materialTitlePart) {
      // return materialRepo.findMatTitleByMatTitleContaining(materialTitlePart);

    //}

    //public List<String> getMaterialsByTitlePartAndEquipment(String materialTitlePart, UUID equipId) {
      //  return materialEquipmentRepo.findMatTitleByEquipmentIdAndMatTitleContaining(equipId,materialTitlePart);
    //}



    public Set<String> getMaterialsByUser(UUID userId ){
        List<MaterialEquipment> materialEquipments=executorEquipmentRepo.findMaterialEquipmentByExecutorId(userId);
        Set<String> materials=new HashSet<>();
        for(MaterialEquipment matEquipment:materialEquipments){
            materials.add(matEquipment.getMaterial().getMatTitle());
        }
        log.info("Get materials by userId: {}",userId);
        return materials;
    }

    public List<String> getMaterialsByEquipment(UUID equipId) {
        List<String> materials= materialEquipmentRepo.findMatTitleByEquipmentId(equipId);
        log.info("Get materials by equipId: {}",equipId);
        return materials;
    }

    public List<String> getAllMaterials() {
        List<String> materials= materialRepo.findAllMatTitles();
        log.info("Get all materials");
        return materials;

    }

    public List<String> MaterialSetToMatTitleList(Set<Material> materials) {
        log.debug("MaterialSet to materialList");
        List<String> result = new ArrayList<>();
        for (Material material : materials) {
            result.add(material.getMatTitle());
        }
        return result;
    }
}
