﻿using System.ComponentModel.DataAnnotations;

namespace EntreeAPI.Models
{
    public class EmployeeDTO
    {
        [Required]
        public string Name { get; set; }
        [Required]
        public string Address { get; set; }

        [Required]
        public string IdCardNumber { get; set; }

        ///Todo:Photo eltárolása
    }
}
