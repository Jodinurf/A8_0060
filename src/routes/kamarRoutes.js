const KamarController = require('../controllers/kamarController');
const express = require('express');
const router = express.Router();

router.get('/', KamarController.getAllKamar);
router.get('/:id', KamarController.getKamarById);
router.post('/store', KamarController.createKamar);
router.delete('/:id', KamarController.deleteKamar)
router.put('/:id', KamarController.updateKamar);

module.exports = router;