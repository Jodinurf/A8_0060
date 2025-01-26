const MahasiswaController = require('../controllers/mahasiswaController');
const express = require('express');
const router = express.Router();


router.get('/', MahasiswaController.getAllMahasiswa);
router.get('/:id', MahasiswaController.getMahasiswaById);
router.post('/store', MahasiswaController.createMahasiswa);
router.delete('/:id', MahasiswaController.deleteMahasiswa)
router.put('/:id', MahasiswaController.updateMahasiswa);

module.exports = router;