<template>
    <v-container>
        <b-card
             title="Добавление оборудования"
             tag="article"
             style="max-width: 50%;"
             class="mb-2"
        >
            <v-form ref="form" v-model="valid" lazy-validation>
                <div>

                    <v-autocomplete
                        v-model="equipName"
                        :items="items"
                        :search-input.sync="search"
                        cache-items
                        hide-no-data
                        hide-details
                        label="Наименование"
                        chips
                        :rules="[v => !!v || 'Выберите оборудование ']"
                    ></v-autocomplete>
                    <div class="mt-2"></div>

                    <v-text-field type="text" placeholder="Описание" v-model="equipDesc" :rules="[v => ( v.length <= 100) || 'Должно быть не более 100 символов']" ></v-text-field>
                    <div class="mt-2"></div>
                    <div>Допустимая высота:  {{height}}</div>
                    <div class="mt-2"></div>

                    <div>Допустимая ширина:  {{width}}</div>
                    <div class="mt-2"></div>

                     <div>Допустимая длина:  {{length}}</div>
                     <div class="mt-2"></div>
                     <v-autocomplete
                        v-model="materialName"
                        :items="materialItems"
                        hide-no-data
                        hide-details
                        label="Материалы"
                        multiple
                        chips
                        :rules="[v => !!v || 'Выберите не менее одиного материала']"
                    ></v-autocomplete>
                    <div class="mt-2"></div>

                </div>
            </v-form>
            <v-btn v-on:click="addEquip" variant="primary">Добавить</v-btn>
            <div class="mt-2"></div>
            <b-alert show dismissible fade>
                <div>
                Если Вы не нашли нужное оборудование в нашей базе, можете зарегистрировать своё оборудование вручную
                </div>
                <div class="mt-2"></div>
                <v-btn to="/create_equipment" variant="primary" :disabled="!valid">Зарегистрировать новое оборудование</v-btn>

            </b-alert>
        </b-card>
    </v-container>
</template>

<script>
import {AXIOS} from './http-common'
export default {
    name: "AddEquipment",
    data () {
        return {
            equipName: null,
            equipDesc: '',
            height:'',
            width: '',
            length: '',
            items: [],
            search: null,
            equipId:null,
            materialName:null,
            materialItems:[],
            valid:true
        }
     },
    watch: {
        search (val) {
            val && val !== this.equipName && this.querySelections(val)
        },
         equipName:'getEquipment',
         },
     methods: {
        addEquip(){
            if(this.$refs.form.validate() && this.$data.equipName!=null){
            let newEquip = {
               'equipName': this.$data.equipName,
               'equipDesc': this.$data.equipDesc,
               'materialList':this.$data.materialName
            };

            console.log(newEquip);
            AXIOS.post('/equipment/add', newEquip)
               .then(response => {
                    this.successAlert();
                }).catch(error => console.log(error));


            }
        },
        successAlert() {
            this.equipName = null;
            this.equipDesc = '';
            this.height = '';
            this.width = '';
            this.length = '';
            this.$router.push('/equipment');
            location.reload()
        },
        querySelections (equipPartName) {
            setTimeout(()=>{
                if(equipPartName==this.$data.search){
                AXIOS.get('/equipment/equipByPartName/'+equipPartName).then((response) =>{
                    this.items=response.data;
                    }).catch(error => console.log(error));
                      }},1200)

                  },
        getEquipment(equipName){
        AXIOS.get('/equipment/name/'+ equipName)
            .then(response => {
                this.$data.height=response.data.height;
                this.$data.width=response.data.width;
                this.$data.length=response.data.length;
                this.$data.equipId=response.data.id;
                this.materialSelections(response.data.id);
            }).catch(error => console.log(error));
        },
        materialSelections(equipId){
                AXIOS.get('/material/equip/'+ equipId).then((response) =>{
                    this.materialItems=response.data;
                    }).catch(error => console.log(error));
        }

     }

}
</script>

<style scoped>
.card {
        margin-left: 282px;
        margin-top: 15px;
}
.card-title {
    margin-left: 101px;
    margin-top: 10px;
    margin-bottom: 45px;
}
</style>