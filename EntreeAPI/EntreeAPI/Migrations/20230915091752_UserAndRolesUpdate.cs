using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace EntreeAPI.Migrations
{
    public partial class UserAndRolesUpdate : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Guests_Users_userId",
                table: "Guests");

            migrationBuilder.DropIndex(
                name: "IX_Guests_userId",
                table: "Guests");

            migrationBuilder.RenameColumn(
                name: "userId",
                table: "Guests",
                newName: "UserId");

            migrationBuilder.AddColumn<int>(
                name: "SportFacilityId",
                table: "Admins",
                type: "int",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_Guests_UserId",
                table: "Guests",
                column: "UserId");

            migrationBuilder.CreateIndex(
                name: "IX_Admins_SportFacilityId",
                table: "Admins",
                column: "SportFacilityId");

            migrationBuilder.AddForeignKey(
                name: "FK_Admins_SportFacilities_SportFacilityId",
                table: "Admins",
                column: "SportFacilityId",
                principalTable: "SportFacilities",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_Guests_Users_UserId",
                table: "Guests",
                column: "UserId",
                principalTable: "Users",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Admins_SportFacilities_SportFacilityId",
                table: "Admins");

            migrationBuilder.DropForeignKey(
                name: "FK_Guests_Users_UserId",
                table: "Guests");

            migrationBuilder.DropIndex(
                name: "IX_Guests_UserId",
                table: "Guests");

            migrationBuilder.DropIndex(
                name: "IX_Admins_SportFacilityId",
                table: "Admins");

            migrationBuilder.DropColumn(
                name: "SportFacilityId",
                table: "Admins");

            migrationBuilder.RenameColumn(
                name: "UserId",
                table: "Guests",
                newName: "userId");

            migrationBuilder.CreateIndex(
                name: "IX_Guests_userId",
                table: "Guests",
                column: "userId",
                unique: true);

            migrationBuilder.AddForeignKey(
                name: "FK_Guests_Users_userId",
                table: "Guests",
                column: "userId",
                principalTable: "Users",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }
    }
}
