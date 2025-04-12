document.addEventListener('DOMContentLoaded', function() {
    // Elementos del DOM
    const deleteModal = document.getElementById('deleteModal');
    const confirmInput = document.getElementById('confirmInput');
    const cancelBtn = document.getElementById('cancelBtn');
    const confirmBtn = document.getElementById('confirmBtn');
    const modalContent = document.querySelector('.modal-content');

    let personaToDelete = null;

    // Delegación de eventos mejorada
    document.addEventListener('click', function(e) {
        const deleteBtn = e.target.closest('.delete-btn');

        if (deleteBtn) {
            e.preventDefault();
            const personaItem = deleteBtn.closest('.persona-item');

            if (!personaItem) {
                console.error('No se encontró el contenedor de persona');
                return;
            }

            // Selección más robusta usando data attributes
            personaToDelete = {
                dni: deleteBtn.getAttribute('data-dni'),
                nombre: personaItem.querySelector('[data-field="nombre"]').textContent,
                apellido: personaItem.querySelector('[data-field="apellido"]').textContent,
                email: personaItem.querySelector('[data-field="email"]').textContent
            };

            showModal();
        }
    });

    // Resto del código permanece igual...
    function showModal() {
        if (!deleteModal) {
            console.error('Modal no encontrado');
            return;
        }

        deleteModal.classList.add('active');
        document.body.classList.add('modal-open');
        confirmInput.value = '';
        confirmInput.focus();
    }

    function hideModal() {
        if (!deleteModal) return;
        deleteModal.classList.remove('active');
        document.body.classList.remove('modal-open');
        personaToDelete = null;
    }

    // ... (resto de las funciones se mantienen igual)
});
